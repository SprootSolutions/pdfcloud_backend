package com.pdf.restapi.service.compression;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class ZipCompressionStrategyImpl implements ZipCompressionStrategy {

    @Override
    public byte[] compressToZip(MultipartFile[] files) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ZipOutputStream zipOut = new ZipOutputStream(baos)) {
            for (MultipartFile file : files) {
                String filename = file.getOriginalFilename();
                if (filename != null && filename.toLowerCase().endsWith(".pdf")) {
                    zipOut.putNextEntry(new ZipEntry(filename));
                    zipOut.write(file.getBytes());
                    zipOut.closeEntry();
                }
            }
        }
        return baos.toByteArray();
    }
}
