package co.id.RyanPratama.MavenMahasiswa.repository;

import co.id.RyanPratama.MavenMahasiswa.entity.Mahasiswa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MahasiswaRepository extends JpaRepository<Mahasiswa, Integer> {
}
