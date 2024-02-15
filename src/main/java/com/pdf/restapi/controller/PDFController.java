package com.pdf.restapi.controller;

import com.pdf.restapi.model.User;
import com.pdf.restapi.service.compression.CompressionPDFStrategy;
import com.pdf.restapi.service.compression.CompressionPDFStrategyImpl;
import com.pdf.restapi.service.merge.MergePDF;
import com.pdf.restapi.service.merge.MergePDFImpl;
import com.pdf.restapi.service.rotate.RotatorPDF;
import com.pdf.restapi.service.split.SplitterPDF;
import com.pdf.restapi.service.split.SplitterPDFImpl;
import com.pdf.restapi.service.сonverters.ConverterPDFtoJPEG;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/pdf")
public class PDFController {


    @GetMapping("/user")
    public ResponseEntity<User> getUser(@RequestParam int id) {
        User user = new User(id, "Mikhail", "Melnik");
        return ResponseEntity.ok(user);
    }

    @PostMapping("compress")
    public ResponseEntity<Resource> compressPDFs(@RequestParam("files") MultipartFile[] files) throws IOException {
        CompressionPDFStrategy compressionPDFStrategy = new CompressionPDFStrategyImpl();
        byte[] compressedFiles = compressionPDFStrategy.compressPDF(files, 5);
//        ZipCompressionStrategy zip = new ZipCompressionStrategyImpl();
//        byte[] compressedFiles = zip.compressToZip(files);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "compressed_pdfs.zip");

        ByteArrayResource resource = new ByteArrayResource(compressedFiles);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(compressedFiles.length)
                .body(resource);
    }

    @PostMapping("/split")
    public ResponseEntity<byte[]> splitPDFs(@RequestParam("file") MultipartFile[] files) throws IOException {
        SplitterPDF splitPDF = new SplitterPDFImpl();
        byte[] zipData = splitPDF.splitPDFs(files);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=splitPDFs.zip");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok()
                .headers(headers)
                .body(zipData);
    }

    @PostMapping("/merge")
    public ResponseEntity<Resource> mergePDFs(@RequestParam("files") MultipartFile[] files) throws IOException {
        MergePDF mergePDF = new MergePDFImpl();
        byte[] mergedFiles = mergePDF.mergePDFs(files);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "merged_pdfs.pdf");

        ByteArrayResource resource = new ByteArrayResource(mergedFiles);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(mergedFiles.length)
                .body(resource);
    }

    @PostMapping("/convert_pdf_to_jpeg")
    public ResponseEntity<byte[]> convertPDFsToJPEG(@RequestParam("files") MultipartFile[] pdfFiles) throws IOException {
        ConverterPDFtoJPEG pdfConverter = new ConverterPDFtoJPEG();
        byte[] zipData = pdfConverter.convertPDFsToJPEGsAndZip(pdfFiles);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=converted_images.zip");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok()
                .headers(headers)
                .body(zipData);
    }

    @PostMapping("/rotate")
    public ResponseEntity<byte[]> rotatePDFs(@RequestParam("files") MultipartFile[] pdfFiles,
                                             @RequestParam int rotationAngle) throws IOException {
        RotatorPDF rotator = new RotatorPDF();
        byte[] rotatedPDFs = rotator.rotatePDFs(pdfFiles, rotationAngle);


        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=rotated_pdfs.zip");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok()
                .headers(headers)
                .body(rotatedPDFs);
    }


}