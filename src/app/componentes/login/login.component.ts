import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { SalutarToken } from 'src/app/model/SalutarToken';
import { Usuario } from 'src/app/model/Usuario';
import { LoginService } from 'src/app/servicos/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  public usuario: Usuario = new Usuario();
  public loading: boolean = false;
  public mensagem: string = "";
  public constructor(private route: Router, private service: LoginService) {

  }

  public logar() {
    this.loading = true;
    this.service.efetuarLogin(this.usuario).subscribe({
      next: (res: SalutarToken) => {
        this.mensagem = "Login efetuado com sucesso"
        setTimeout(() => { this.mensagem = ""; }, 3000); // limpa em 3 segundos
        this.loading = false;
        localStorage.setItem("SalutarTK", res.token);
        this.route.navigate(['main'])
      },
      error: (err: any) => {
        this.mensagem = "Usuário ou senha inválidos"
        setTimeout(() => { this.mensagem = ""; }, 3000); // limpa em 3 segundos
        this.loading = false;
      }
    });
  }
}
