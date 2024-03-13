package translator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import translator.dto.EnWordDto;
import translator.service.EnWordService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/EnToRu")
public class EnRuController {

    private final EnWordService enWordService;

    @Autowired
    public EnRuController(EnWordService enWordService) {
        this.enWordService = enWordService;
    }

    /**
     * GET request to "/translate/{word}" Translates word from english to russian
     *
     * @param word En word to translate
     * @return Translation of specified if translate word presents otherwise null
     */
    @GetMapping("/translate/{word}")
    public EnWordDto translateRuByEn(@PathVariable String word) {
        return enWordService.getByWordName(word);
    }

    /**
     * GET request to "/translateSequence" Translates word from english to russian
     *
     * @param sequence (String) sequence to translate
     * @return translated specified sequence from english (if word presents in dictionary) to russian
     */
    @GetMapping("/translateSequence")
    public List<String> appendWord(@RequestBody String sequence) {
        return enWordService.translateSequence(sequence);
    }

    /**
     * POST request to "/words/save" to save specified words association En - Ru
     *
     * @param enWord (String) specified En word, associated with Ru word
     * @param ruWord (String) specified Ru word, associated with En word
     * @return HTTP status of operation
     */
    @PostMapping("/words/save")
    public ResponseEntity<EnWordDto> saveEnWord(@RequestParam String enWord, @RequestParam String ruWord) {
        EnWordDto enWordDto = new EnWordDto(enWord);
        enWordDto.addRuWord(ruWord);
        return ResponseEntity.ok(enWordService.save(enWordDto));
    }

    /**
     * POST request to "/words/saveWords" to save En word as Object, which associated with specified Ru words
     *
     * @param enWordDto (Object) specified En word, which associated with Ru words
     * @return HTTP status of operation
     */
    @PostMapping("/words/saveWords")
    public ResponseEntity<EnWordDto> saveEnWordDto(@Valid @RequestBody EnWordDto enWordDto) {
        return ResponseEntity.ok(enWordService.save(enWordDto));
    }

    /**
     * Delete request to "/words/delete{word}" to delete specified En word
     *
     * @param word word to delete
     * @return HTTP status of operation
     */
    @DeleteMapping("/words/delete/{word}")
    public ResponseEntity<EnWordDto> deleteWord(@PathVariable String word) {
        return ResponseEntity.ok(enWordService.delete(new EnWordDto(word)));
    }

    /**
     * Put request to "/words/updateWords" to replace word
     *
     * @param enWord  (String) replaced Ru word
     * @param replace (String) Ru word to replace enWord
     * @return HTTP status of operation
     */
    @PutMapping("/words/updateWords")
    public ResponseEntity<EnWordDto> updateWord(@RequestParam String enWord, @RequestParam String replace) {
        return ResponseEntity.ok(enWordService.update(new EnWordDto(enWord), new EnWordDto(replace)));
    }

    /**
     * Put request to "/words/update" to replace word
     *
     * @param enWord  (Object) replaced Ru word
     * @param replace (Object) Ru word to replace enWord
     * @return HTTP status of operation
     */
    @PutMapping("/words/update")
    public ResponseEntity<EnWordDto> updateWord(@Valid @RequestBody EnWordDto enWord, @Valid @RequestBody EnWordDto replace) {
        return ResponseEntity.ok(enWordService.update(enWord, replace));
    }

    /**
     * Patch request to "/words/append" to append new translate(as Ru word) to En word
     *
     * @param enWord En word for which the translation will be added
     * @param ruWord Ru word as a new translation of En word
     * @return HTTP status of operation
     */
    @PatchMapping("/words/append")
    public ResponseEntity<EnWordDto> appendWord(@RequestParam String enWord, @RequestParam String ruWord) {
        return ResponseEntity.ok(enWordService.addNewTranslation(new EnWordDto(enWord), ruWord));
    }
}
