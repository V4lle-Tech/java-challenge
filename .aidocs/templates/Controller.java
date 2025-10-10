@RestController
@RequestMapping("/api/v1/entities")
@RequiredArgsConstructor
@Slf4j
public class EntityController {
    private final EntityService entityService;
    
    @GetMapping("/{id}")
    public ResponseEntity<EntityDTO> getById(@PathVariable Long id) {
        return entityService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
