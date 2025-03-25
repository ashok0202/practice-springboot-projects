package com.tekworks.docker_mysql.repository;

import com.tekworks.docker_mysql.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

}
