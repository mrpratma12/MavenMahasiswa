package co.id.RyanPratama.MavenMahasiswa.controller;

import co.id.RyanPratama.MavenMahasiswa.entity.Mahasiswa;
import co.id.RyanPratama.MavenMahasiswa.service.MahasiswaService;
import co.id.RyanPratama.MavenMahasiswa.util.ExcelGenerator;
import co.id.RyanPratama.MavenMahasiswa.util.GeneratePdfReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayInputStream;
import java.util.List;

@Controller
@RequestMapping(value = "/")
public class MahasiswaController {
    @Autowired
    private MahasiswaService mahasiswaService;

    @Autowired
    private ExcelGenerator excelGenerator;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("mahasiswas", mahasiswaService.getMahasiswas());
        return "index";
    }

    @GetMapping(value = "tambah")
    public String showTambahForm(Model model) {
        model.addAttribute("mahasiswa", new Mahasiswa());
        return "tambah";
    }

    @PostMapping(value = "tambah")
    public String tambahMahasiswaBaru(@ModelAttribute("mahasiswa") Mahasiswa mahasiswa) {
        mahasiswaService.saveMahasiswa(mahasiswa);
        return "redirect:/";
    }

    @GetMapping(value = "edit/{id}")
    public String editMahasiswa(Model model, @PathVariable("id") Integer id) {
        Mahasiswa mahasiswa = mahasiswaService.getMahasiswa(id);
        model.addAttribute("mahasiswa", mahasiswa);
        return "edit";
    }

    @PostMapping(value = "edit")
    public String updateMahasiswa(@ModelAttribute("mahasiswa") Mahasiswa mahasiswa) {
        mahasiswaService.updateMahasiswa(mahasiswa);
        return "redirect:/";
    }

    @GetMapping(value = "delete/{id}")
    public String deletingMahasiswa(@PathVariable("id") Integer id) {
        Mahasiswa mahasiswa = mahasiswaService.getMahasiswa(id);
        mahasiswaService.deleteMahasiswa(mahasiswa);
        return "redirect:/";
    }

    @GetMapping(value = "pdf",
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> listMahasiswaReport() {

        List<Mahasiswa> mahasiswaList = mahasiswaService.getMahasiswas();

        ByteArrayInputStream bis = GeneratePdfReport.listMahasiswasReport(mahasiswaList);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=List-Mahasiswa.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping("/xls")
    public ResponseEntity<InputStreamResource> excelMahasiswaReport() throws Exception {
        List<Mahasiswa> mahasiswaList = mahasiswaService.getMahasiswas();

        ByteArrayInputStream in = excelGenerator.exportExcel(mahasiswaList);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=List-Mahasiswa.xlsx");

        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));

    }
}
