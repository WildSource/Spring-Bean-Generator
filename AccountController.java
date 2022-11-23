@RestController
@RequestMapping("/v1/entity")
public class AccountController {

   private AccountService accountService;

   @AutoWired
   public AccountController(AccountService accountService) {
       accountService.this = accountService;
   }
}