package dropboxweb;

import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxWriteMode;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * Created by ginger on 16.05.16.
 */
public class UploadFilePositiveTest  extends BaseTest {
    String txtFile = "uploadFile.txt";
    String jpgFile = "upload6000x6000.jpg";
    String zipFile = "uploadMaven.zip";
    String djvuFile = "uploadBook.djvu";
    String gifFile = "uploadMore10mb.gif";

    @DataProvider(name = "uploadFileName")
    public Object[][] simpleDataProvider() {
        return new Object[][]{
                {txtFile},
                {jpgFile},
                {zipFile},
                {djvuFile},
                {gifFile}
        };
    }

    @Test(dataProvider = "uploadFileName")
    public void uploadValidFile(String uploadFileName) throws IOException, DbxException {
        File inputFile = new File("src/main/resources/" + uploadFileName);
        FileInputStream inputStream = new FileInputStream(inputFile);
        try {
            DbxEntry.File uploadedFile = client.uploadFile("/" + uploadFileName,
                    DbxWriteMode.add(), inputFile.length(), inputStream);
            System.out.println("Uploaded: " + uploadedFile.toString());
        } finally {
            inputStream.close();
        }

        FileOutputStream outputStream = new FileOutputStream(uploadFileName);
        try {
            DbxEntry.File downloadedFile = client.getFile("/" + uploadFileName, null,
                    outputStream);
            assertEquals(downloadedFile.name, uploadFileName, "Expected file is not in dropbox folder.");
        } finally {
            outputStream.close();
        }
    }

    @AfterSuite()
    public void deleteFile() throws DbxException {
        List<DbxEntry> listOfFiles = client.searchFileAndFolderNames("/", "upload");
        listOfFiles.stream().forEach(file -> {
            try {
                client.delete("/" + file.name);
            } catch (DbxException e) {
                System.out.println("Error deleting " + file.name + " file");
            }
        });
    }

}
