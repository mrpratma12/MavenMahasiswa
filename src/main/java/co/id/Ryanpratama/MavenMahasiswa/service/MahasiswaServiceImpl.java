package co.id.RyanPratama.MavenMahasiswa.service;

import co.id.RyanPratama.MavenMahasiswa.entity.Mahasiswa;
import co.id.RyanPratama.MavenMahasiswa.exception.ResourceNotFoundException;
import co.id.RyanPratama.MavenMahasiswa.repository.MahasiswaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MahasiswaServiceImpl implements MahasiswaService{
    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    @Override
    public List<Mahasiswa> getMahasiswas() {
        return mahasiswaRepository.findAll();
    }

    @Override
    public Mahasiswa getMahasiswa(Integer id) {
        return mahasiswaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Mahasiswa [mahasiswaId=" + id + "] " +
                "can't be found"));

    }

    @Override
    public Mahasiswa saveMahasiswa(Mahasiswa mahasiswa) {
        return mahasiswaRepository.save(mahasiswa);
    }

    @Override
    public Mahasiswa updateMahasiswa(Mahasiswa mahasiswa) {
        return mahasiswaRepository.findById(mahasiswa.getId()).map(mahasiswaTemp -> {
            mahasiswaTemp.setId(mahasiswa.getId());
            mahasiswaTemp.setNim(mahasiswa.getNim());
            mahasiswaTemp.setNama(mahasiswa.getNama());
            mahasiswaTemp.setAlamat(mahasiswa.getAlamat());
            mahasiswaRepository.save(mahasiswaTemp);
            return mahasiswaTemp;
        }).orElseThrow(() -> new ResourceNotFoundException("Mahasiswa [mahasiswaId=" + mahasiswa.getId() + "] can't be found"));

    }

    @Override
    public void deleteMahasiswa(Mahasiswa mahasiswa) {

        mahasiswaRepository.findById(mahasiswa.getId()).orElseThrow(() -> new ResourceNotFoundException("mahasiswa " +
                "[mahasiswaId=" + mahasiswa.getId() + "] can't be found"));
        mahasiswaRepository.deleteById(mahasiswa.getId());


    }
}
