package co.id.RyanPratama.MavenMahasiswa.util;


import co.id.RyanPratama.MavenMahasiswa.entity.Mahasiswa;
import co.id.RyanPratama.MavenMahasiswa.repository.MahasiswaRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import java.util.List;

@Component
public class ExcelGenerator {
    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    /* Count Row of Excel Table */
    public static int CountRowExcel(Iterator<Row> iterator) {
        int size = 0;
        while (iterator.hasNext()) {
            Row row = iterator.next();
            size++;
        }
        return size;
    }

    /* export */
    public ByteArrayInputStream exportExcel(List<Mahasiswa> mahasiswaList) throws Exception {
        String[] columns = {"Id", "Nim", "Nama", "Alamat"};
        try (
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream()
        ) {
            CreationHelper creationHelper = workbook.getCreationHelper();
            Sheet sheet = workbook.createSheet("Data List mahasiswa");
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.BLUE.getIndex());
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            //Row of Header
            Row headerRow = sheet.createRow(0);

            //Header
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerCellStyle);
            }


            int rowIdx = 1;
            for (Mahasiswa mahasiswa : mahasiswaList) {
                Row row = sheet.createRow(rowIdx);

                row.createCell(0).setCellValue(mahasiswa.getId());
                row.createCell(1).setCellValue(mahasiswa.getNim());
                row.createCell(2).setCellValue(mahasiswa.getNama());
                row.createCell(3).setCellValue(mahasiswa.getAlamat());
                rowIdx++;
            }

            workbook.write(out);
            workbook.close();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {

        }
        return null;
    }

    /* Import */
    public void importExcel(MultipartFile file) throws Exception {

        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (int i = 0; i < (CountRowExcel(sheet.rowIterator())); i++) {
            if (i == 0) {
                continue;
            }

            Row row = sheet.getRow(i);

            String nim = row.getCell(1).getStringCellValue();
            String nama = row.getCell(2).getStringCellValue();
            String alamat = row.getCell(3).getStringCellValue();

            mahasiswaRepository.save(new Mahasiswa(0, nim, nama, alamat));
        }

    }
}
