@Service
public class ComicService {

    private ComicService comicRepository;

    public ComicService() {}

    @AutoWired
    public ComicService(ComicService comicRepository) {
        this.comicRepository = comicRepository;
    }
}