package top.zopx.goku.example.controller;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.File;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * @author 谢先生
 * @email xiezhyan@126.com
 * @date 2022/12/05 22:07
 */
@RestController
@RequestMapping("/upload")
public class UploadFileController {

    @PostMapping("/single")
    public Mono<String> uploadSingleFile(@RequestPart("file") FilePart filePart) {
        return filePart.content()
                .map(DataBuffer::asInputStream)
                .reduce(SequenceInputStream::new)
                .publishOn(Schedulers.boundedElastic())
                .flatMap(inputStream -> {
                    File file = null;
                    try {
                        file = new File("single." + filePart.filename());
                        Files.copy(inputStream, file.toPath());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    filePart.delete().subscribe();
                    return Mono.just(file.getAbsolutePath());
                });
    }

    @PostMapping("/uploadSingle")
    public Mono<String> single(@RequestPart ("file") Mono<FilePart> file) throws IOException {
        return file.map(filePart->{
            Path tempFile = null;
            try {
                tempFile = Files.createTempFile("single", filePart.filename());
            } catch (IOException e) {
                e.printStackTrace();
            }
            AsynchronousFileChannel channel = null;
            try {
                channel = AsynchronousFileChannel.open(tempFile, StandardOpenOption.WRITE);
            } catch (IOException e) {
                e.printStackTrace();
            }
            DataBufferUtils.write(filePart.content(), channel, 0).doOnComplete(() -> {
                System.out.println("finish....");
            }).subscribe();
            return tempFile;
        }).map(a->{
            return a.toFile();
            //此时已转换为File类，具体的业务逻辑我就忽略了
        }).flatMap(fileSinge-> Mono.justOrEmpty(fileSinge.getAbsolutePath()));
    }
}
