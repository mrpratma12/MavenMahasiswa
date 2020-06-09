package co.id.RyanPratama.MavenMahasiswa.service;

import co.id.RyanPratama.MavenMahasiswa.entity.Mahasiswa;

import java.util.List;

public interface MahasiswaService {
    List<Mahasiswa> getMahasiswas();

    Mahasiswa getMahasiswa(Integer id);

    Mahasiswa saveMahasiswa(Mahasiswa mahasiswa);

    Mahasiswa updateMahasiswa(Mahasiswa mahasiswa);

    void deleteMahasiswa(Mahasiswa mahasiswa);


}
