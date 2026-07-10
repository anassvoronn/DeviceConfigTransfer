package org.nastya.service;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ZipService {

    public byte[] zip(Path file) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try (ZipOutputStream zip = new ZipOutputStream(baos)) {

            zip.putNextEntry(new ZipEntry(file.getFileName().toString()));

            Files.copy(file, zip);

            zip.closeEntry();
        }

        return baos.toByteArray();
    }
}