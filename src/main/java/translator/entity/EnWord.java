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
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Table(name = "EnWord")
public class EnWord extends AbstractWord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "EnRuWords",
            joinColumns = @JoinColumn(name = "En_Id"),
            inverseJoinColumns = @JoinColumn(name = "Ru_Id"))
    private List<RuWord> RuWords;

    public EnWord(String word) {
        super(word);
    }

    public void addWord(String word) {
        if (RuWords == null) {
            RuWords = new ArrayList<>();
        }
        RuWords.add(new RuWord(word));
    }

    @Override
    public String toString() {
        return "EnWord{id=" + id + ", word='" + super.getWord() + '}';
    }
}