package org.nastya.service;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Service
public class ZipService {

    public byte[] zip(Path file) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ZipOutputStream zip = new ZipOutputStream(baos)) {

            zip.putNextEntry(new ZipEntry(file.getFileName().toString()));

            Files.copy(file, zip);

            zip.closeEntry();

            return baos.toByteArray();
        }
    }

    public Path unzip(Path zipFile, Path outputDirectory) throws IOException {
        try (ZipInputStream zip = new ZipInputStream(Files.newInputStream(zipFile))) {

            ZipEntry entry = zip.getNextEntry();

            if (entry == null) {
                throw new IOException("ZIP archive is empty");
            }

            Path outputFile = outputDirectory.resolve(entry.getName());

            Files.copy(zip, outputFile, StandardCopyOption.REPLACE_EXISTING);

            return outputFile;
        }
    }

}
