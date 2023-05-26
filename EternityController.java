@Controller
public class EternityController {

    private EternityService eternityService;

    @AutoWired
    public EternityController(EternityService eternityService) {
        this.eternityService = eternityService;
    }

}