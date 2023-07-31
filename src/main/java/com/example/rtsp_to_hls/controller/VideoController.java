package com.example.rtsp_to_hls.controller;

import com.github.kokorin.jaffree.StreamType;
import com.github.kokorin.jaffree.ffmpeg.FFmpeg;
import com.github.kokorin.jaffree.ffmpeg.PipeOutput;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

@RestController
public class VideoController {

    @GetMapping("/video")
    @ResponseBody
    public ResponseEntity<StreamingResponseBody> livestream() {

        String rtspUrl = "rtsp://210.99.70.120:1935/live/cctv001.stream";

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(os -> {
                    FFmpeg.atPath(Path.of("C:\\Users\\이진석\\Desktop\\rtsp_to_hls\\src\\main\\resources\\ffmpeg\\bin"))
                            .addArgument("-re")
                            .addArguments("-acodec", "pcm_s16le")
                            .addArguments("-rtsp_transport", "tcp")
                            .addArguments("-i", rtspUrl)
                            .addArguments("-vcodec", "copy")
                            .addArguments("-af", "asetrate=22050")
                            .addArguments("-acodec", "aac")
                            .addArguments("-b:a", "96k" )
                            .addOutput(PipeOutput.pumpTo(os)
                                    .disableStream(StreamType.AUDIO)
                                    .disableStream(StreamType.SUBTITLE)
                                    .disableStream(StreamType.DATA)
                                    .setFrameCount(StreamType.VIDEO, null)
                                    //1 frame every 10 seconds
                                    .setFrameRate(0.1)
                                    .setDuration(1, TimeUnit.HOURS)
                                    .setFormat("ismv"))
                            .execute();
                });
    }
}
