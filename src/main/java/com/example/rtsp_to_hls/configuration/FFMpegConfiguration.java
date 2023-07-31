package com.example.rtsp_to_hls.configuration;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFprobe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

public class FFMpegConfiguration {

    @Value("${ffmpeg.exe.location}")
    private String ffmpegLocation;

    @Value("${ffprobe.exe.location}")
    private String ffprobeLocation;

    @Bean(name = "ffMpeg")
    public FFmpeg ffMpeg() throws IOException {
        FFmpeg ffMpeg = null;

        String osName = System.getProperty("os.name");

        // 운영체제가 윈도우인 경우 jar에 내장되어 있는 ffmpeg을 이용
        if (osName.toLowerCase().contains("win")) {
            ClassPathResource classPathResource = new ClassPathResource(ffmpegLocation);
            ffMpeg = new FFmpeg(classPathResource.getURL().getPath());
        } else if (osName.toLowerCase().contains("unix") || osName.toLowerCase().contains("linux")) {
            ffMpeg = new FFmpeg(ffmpegLocation);
        }

        return ffMpeg;
    }

    @Bean(name = "ffProbe")
    public FFprobe ffProbe() throws IOException {
        FFprobe ffprobe = null;

        String osName = System.getProperty("os.name");

        // 운영체제가 Window인 경우 jar에 내장되어있는 ffmpeg 를 이용
        if (osName.toLowerCase().contains("win")) {
            ClassPathResource classPathResource = new ClassPathResource(ffprobeLocation);
            ffprobe = new FFprobe(classPathResource.getURL().getPath());
        } else if(osName.toLowerCase().contains("unix") || osName.toLowerCase().contains("linux")) {
            ffprobe = new FFprobe(ffmpegLocation);
        }

        return ffprobe;
    }
}
