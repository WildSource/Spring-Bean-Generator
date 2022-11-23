@RestController
@RequestMapping("/v1/entity")
public class AccountController {

   private AccountService accountService;

   @AutoWired
   public AccountController(AccountService accountService) {
       accountService.this = accountService;
   }

   @GetMapping
   public  getAccount() {

   }

   @PostMapping
   public  postAccount() {

   }

   @PutMapping
   public  putAccount() {

   }

   @DeleteMapping
   public  deleteAccount() {

   }

}