package com.pdf.restapi.service.compression;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ZipCompressionStrategy {
    byte[] compressToZip(MultipartFile[] files) throws IOException;
}
