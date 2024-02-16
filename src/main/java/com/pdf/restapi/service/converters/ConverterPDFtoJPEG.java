package com.pdf.restapi.service.converters;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ConverterPDFtoJPEG {

    public byte[] convertPDFsToJPEGsAndZip(MultipartFile[] pdfFiles) throws IOException {
        List<byte[]> jpegImages = new ArrayList<>();

        for (MultipartFile pdfFile : pdfFiles) {
            try (PDDocument document = PDDocument.load(pdfFile.getInputStream())) {
                PDFRenderer pdfRenderer = new PDFRenderer(document);

                for (int page = 0; page < document.getNumberOfPages(); ++page) {
                    BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(bim, "jpeg", baos);
                    jpegImages.add(baos.toByteArray());
                }
            }
        }

        ByteArrayOutputStream zipBaos = new ByteArrayOutputStream();
        try (ZipOutputStream zipOut = new ZipOutputStream(zipBaos)) {
            for (int i = 0; i < jpegImages.size(); i++) {
                byte[] jpegImage = jpegImages.get(i);
                ZipEntry zipEntry = new ZipEntry("page_" + i + ".jpeg");
                zipOut.putNextEntry(zipEntry);
                zipOut.write(jpegImage);
            }
        }

        return zipBaos.toByteArray();
    }
}
