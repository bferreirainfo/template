package business.usuario;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "Usuario")
public class Usuario {

    @Id
    @GeneratedValue
    @Column(name = "USUARIO_ID")
    //se não tiver @Column, o nome da coluna será o próprio nome da entidade no caso "id" 
    private Long id;

    @NotEmpty
    @Length(max = 60)
    @Column(name = "USUARIO_NOME")
    private String nome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
