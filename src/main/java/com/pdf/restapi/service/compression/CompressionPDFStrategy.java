package com.pdf.restapi.service.compression;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CompressionPDFStrategy {
    byte[] compressPDF(MultipartFile[] files, int quality) throws IOException;
}
