package com.pdf.restapi.service.split;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@Service
public class SplitterPDFImpl implements SplitterPDF {

    @Override
    public byte[] splitPDFs(MultipartFile[] files) throws IOException {
        ByteArrayOutputStream zipBuffer = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(zipBuffer);

        for (MultipartFile file : files) {
            try (PDDocument document = PDDocument.load(file.getInputStream())) {
                int pageCount = document.getNumberOfPages();
                for (int i = 0; i < pageCount; i++) {
                    PDDocument newDocument = new PDDocument();
                    newDocument.addPage((PDPage)
                            document.getPage(i));

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    newDocument.save(byteArrayOutputStream);
                    newDocument.close();

                    zipOutputStream.putNextEntry(new ZipEntry(file.getOriginalFilename() + "_page" + i + ".pdf"));
                    zipOutputStream.write(byteArrayOutputStream.toByteArray());
                    zipOutputStream.closeEntry();
                }
            }
        }

        zipOutputStream.close();

        return zipBuffer.toByteArray();
    }
}
