package org.seleniumtests.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Comparator;

public class FileUtils {

    // Waits until a new file appears in the folder (based on count difference)
    public static File waitForNewFile(File downloadDir, int initialCount, int timeoutSeconds)
            throws InterruptedException {
        int waited = 0;
        while (waited < timeoutSeconds) {
            File[] files = downloadDir.listFiles();
            if (files != null && files.length > initialCount) {
                // Return the newest file by lastModified time
                return Arrays.stream(files)
                        .max(Comparator.comparingLong(File::lastModified))
                        .orElse(null);
            }
            Thread.sleep(1000);
            waited++;
        }
        return null; // timeout — no new file detected
    }

    // Renames the downloaded file to a unique name to avoid overwrites
    public static File renameDownloadedFile(File originalFile, String newName) throws IOException {
        File renamedFile = new File(originalFile.getParent(), newName);
        Files.move(originalFile.toPath(), renamedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return renamedFile;
    }

}
