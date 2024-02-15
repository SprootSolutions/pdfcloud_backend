package com.pdf.restapi.service.merge;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class MergePDFImpl implements MergePDF {

    @Override
    public byte[] mergePDFs(MultipartFile[] files) throws IOException {
        PDFMergerUtility mergerUtility = new PDFMergerUtility();
        ByteArrayOutputStream mergedPDFOutputStream = new ByteArrayOutputStream();

        for (MultipartFile file : files) {
            mergerUtility.addSource(file.getInputStream());
        }

        mergerUtility.setDestinationStream(mergedPDFOutputStream);
        mergerUtility.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());

        return mergedPDFOutputStream.toByteArray();
    }
}
