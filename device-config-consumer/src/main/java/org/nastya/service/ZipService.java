package org.nastya.service;

import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class ZipService {

    public byte[] unzip(byte[] zipBytes) throws IOException {
        try (
                ZipInputStream zip = new ZipInputStream(new ByteArrayInputStream(zipBytes));
                ByteArrayOutputStream out = new ByteArrayOutputStream()
        ) {
            ZipEntry entry = zip.getNextEntry();

            if (entry == null) {
                throw new IOException("ZIP archive is empty");
            }

            zip.transferTo(out);

            return out.toByteArray();
        }
    }
}