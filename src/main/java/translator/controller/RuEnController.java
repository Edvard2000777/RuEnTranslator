package translator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import translator.dto.RuWordDto;
import translator.service.RuWordService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/RuToEn")
public class RuEnController {

    private final RuWordService ruWordService;

    @Autowired
    public RuEnController(RuWordService ruWordService) {
        this.ruWordService = ruWordService;
    }

    /**
     * GET request to "/translate/{word}" Translates word from russian to english
     *
     * @param word Ru word to translate
     * @return Translation of specified if translate word presents otherwise null
     */
    @GetMapping("/translate/{word}")
    public RuWordDto translateRuByEn(@PathVariable String word) {
        return ruWordService.getByWordName(word);
    }

    /**
     * GET request to "/translateSequence" Translates word from russian to english
     *
     * @param sequence (String) sequence to translate
     * @return translated specified sequence from russian (if word presents in dictionary) to english
     */
    @GetMapping("/translateSequence")
    public List<String> appendWord(@RequestBody String sequence) {
        return ruWordService.translateSequence(sequence);
    }

    /**
     * POST request to "/words/save" to save specified words association Ru - En
     *
     * @param ruWord (String) specified Ru word, associated with En word
     * @param enWord (String) specified En word, associated with Ru word
     * @return HTTP status of operation
     */
    @PostMapping("/words/save")
    public ResponseEntity<RuWordDto> saveRuWord(@RequestParam String ruWord, @RequestParam String enWord) {
        RuWordDto ruWordDto = new RuWordDto(ruWord);
        ruWordDto.addEnWord(enWord);
        return ResponseEntity.ok(ruWordService.save(ruWordDto));
    }

    /**
     * POST request to "/words/saveWords" to save Ru word as Object, which associated with specified En words
     *
     * @param ruWordDto (Object) specified Ru word, which associated with En words
     * @return HTTP status of operation
     */
    @PostMapping("/words/saveWords")
    public ResponseEntity<RuWordDto> saveEnWordDto(@Valid @RequestBody RuWordDto ruWordDto) {
        return ResponseEntity.ok(ruWordService.save(ruWordDto));
    }

    /**
     * Delete request to "/words/delete" to delete specified Ru word
     *
     * @param word word to delete
     * @return HTTP status of operation
     */
    @DeleteMapping("/words/delete/{word}")
    public ResponseEntity<RuWordDto> deleteWord(@PathVariable String word) {
        return ResponseEntity.ok(ruWordService.delete(new RuWordDto(word)));
    }

    /**
     * Put request to "/words/updateWords" to replace word
     *
     * @param ruWord  (String) replaced Ru word
     * @param replace (String) Ru word to replace RuWord
     * @return HTTP status of operation
     */
    @PutMapping("/words/updateWords")
    public ResponseEntity<RuWordDto> updateWord(@RequestParam String ruWord, @RequestParam String replace) {
        return ResponseEntity.ok(ruWordService.update(new RuWordDto(ruWord), new RuWordDto(replace)));
    }

    /**
     * Put request to "/words/update" to replace word
     *
     * @param ruWord  (Object) replaced Ru word
     * @param replace (Object) Ru word to replace ruWord
     * @return HTTP status of operation
     */
    @PutMapping("/words/update")
    public ResponseEntity<RuWordDto> updateWord(@Valid @RequestBody RuWordDto ruWord, @Valid @RequestBody RuWordDto replace) {
        return ResponseEntity.ok(ruWordService.update(ruWord, replace));
    }

    /**
     * Patch request to "/words/append" to append new translate(as Ru word) to En word
     *
     * @param ruWord Ru word for which the translation will be added
     * @param enWord En word as a new translation of Ru word
     * @return HTTP status of operation
     */
    @PatchMapping("/words/append")
    public ResponseEntity<RuWordDto> appendWord(@RequestParam String ruWord, @RequestParam String enWord) {
        return ResponseEntity.ok(ruWordService.addNewTranslation(new RuWordDto(ruWord), enWord));
    }
}
