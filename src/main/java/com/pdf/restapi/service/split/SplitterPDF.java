package com.pdf.restapi.service.split;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface SplitterPDF {
    byte[] splitPDFs(MultipartFile[] files) throws IOException;
}
