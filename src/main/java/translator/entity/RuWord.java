package translator.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "RuWord")
public class RuWord extends AbstractWord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "EnRuWords",
            joinColumns = @JoinColumn(name = "Ru_Id"),
            inverseJoinColumns = @JoinColumn(name = "En_Id"))
    private List<EnWord> EnWords;

    public RuWord(String word) {
        super(word);
    }

    public void addWord(String word) {
        if (EnWords == null) {
            EnWords = new ArrayList<>();
        }
        EnWords.add(new EnWord(word));
    }

    @Override
    public String toString() {
        return "RuWord{id=" + id + ", word='" + super.getWord() + '}';
    }
}