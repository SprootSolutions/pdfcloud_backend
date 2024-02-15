package com.pdf.restapi.service.compression;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class CompressionPDFStrategyImpl implements CompressionPDFStrategy {

    @Override
    public byte[] compressPDF(MultipartFile[] files, int quality) throws IOException {

        return null;
    }
}

