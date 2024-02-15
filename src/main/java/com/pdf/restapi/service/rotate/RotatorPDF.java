package com.pdf.restapi.service.rotate;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class RotatorPDF {

    public byte[] rotatePDFs(MultipartFile[] pdfFiles, int rotationAngle) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try (ZipOutputStream zipOut = new ZipOutputStream(baos)) {
            for (MultipartFile pdfFile : pdfFiles) {
                try (PDDocument document = PDDocument.load(pdfFile.getInputStream())) {
                    document.getPages().forEach((Consumer<PDPage>) page -> {
                        int rotation = switch (rotationAngle) {
                            case 1 -> 90;
                            case 2 -> 180;
                            case 3 -> 270;
                            default -> 0;
                        };
                        page.setRotation(rotation);
                    });

                    ByteArrayOutputStream rotatedPageBaos = new ByteArrayOutputStream();
                    document.save(rotatedPageBaos);
                    ZipEntry zipEntry = new ZipEntry(pdfFile.getOriginalFilename());
                    zipOut.putNextEntry(zipEntry);
                    zipOut.write(rotatedPageBaos.toByteArray());
                    rotatedPageBaos.close();
                }
            }
        }

        return baos.toByteArray();
    }
}
