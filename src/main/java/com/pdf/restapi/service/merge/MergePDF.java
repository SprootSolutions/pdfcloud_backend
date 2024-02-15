package com.pdf.restapi.service.merge;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MergePDF {
    byte[] mergePDFs(MultipartFile[] files) throws IOException;
}
