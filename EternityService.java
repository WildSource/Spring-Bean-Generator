@Service
public class EternityService {

    private EternityRepository eternityRepository;

    public EternityService() {}

    @AutoWired
    public EternityService(EternityRepository eternityRepository) {
        this.eternityRepository = eternityRepository;
    }
}