package com.jh.framework.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by JH on 2018/4/23.
 */

public class VoiceSpeaker {
    private static VoiceSpeaker sInstance;

    private ExecutorService service;

    private VoiceSpeaker() {
        service = Executors.newCachedThreadPool();
    }


    public static synchronized VoiceSpeaker getInstance() {
        if (sInstance == null) {
            sInstance = new VoiceSpeaker();
        }
        return sInstance;
    }


    public void speak(final List<String> list, final Context context) {
        if (service != null) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    start(list, context);
                }
            });
        }
    }

    private void start(final List<String> list, Context context) {
        synchronized (this) {
            final CountDownLatch latch = new CountDownLatch(1);
            MediaPlayer player = new MediaPlayer();
            if (list != null && list.size() > 0) {
                final int[] counter = {0};
                String path = String.format("sound/tts_%s.mp3", list.get(counter[0]));
                AssetFileDescriptor fd = null;
                final AssetManager am = context.getAssets();
                try {
                    fd = am.openFd("musics/SleepOceation.mp3");
                    player.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(),
                            fd.getLength());
                    player.prepareAsync();
                    player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.start();
                        }
                    });
                    player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mp.reset();
                            counter[0]++;
                            if (counter[0] < list.size()) {
                                try {
                                    AssetFileDescriptor fileDescriptor = am.openFd(String.format("sound/tts_%s.mp3", list.get(counter[0])));
                                    mp.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
                                    mp.prepare();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    latch.countDown();
                                }
                            } else {
                                mp.release();
                                latch.countDown();
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    latch.countDown();
                } finally {
                    if (fd != null) {
                        try {
                            fd.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            try {
                latch.await();
                this.notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
