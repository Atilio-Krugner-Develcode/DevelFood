package br.com.develfood.develfood.Class;

import br.com.develfood.develfood.Enum.StatusEmail;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "email")
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ownerRef;
    private String emailFrom;
    private String emailTo;
    private String title;
    @Column(columnDefinition = "TEXTO")
    private  String texto;
    private LocalDateTime tempo;
    private StatusEmail status;


}
