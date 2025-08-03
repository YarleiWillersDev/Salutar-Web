import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DadosCEP } from 'src/app/model/DadosCEP';
import { FichaPaciente } from 'src/app/model/FichaPaciente';
import { PathToFile } from 'src/app/model/PathToFile';
import { CepService } from 'src/app/servicos/cep.service';
import { FichaService } from 'src/app/servicos/ficha.service';
import { UploadService } from 'src/app/servicos/upload.service';

@Component({
  selector: 'app-fichapaciente',
  templateUrl: './fichapaciente.component.html',
  styleUrls: ['./fichapaciente.component.css']
})
export class FichapacienteComponent implements OnInit {

  public ficha: FichaPaciente;
  public loading: boolean = false;
  private idFicha: String = "";
  public mensagemModal: String = "";
  public estiloMensagem: String = "";
  public modalTipo: string = "";
  private pathToFile: PathToFile;
  

  public constructor(private cepService: CepService, private activatedRoute: ActivatedRoute, private fichaService: FichaService, private router: Router, private uploadService: UploadService) {

    this.ficha = new FichaPaciente();
    this.pathToFile = new PathToFile();
    this.idFicha = this.activatedRoute.snapshot.params["id"];

    if (this.idFicha != "NOVA") {
      this.loading = true
      this.fichaService.buscarFichaPeloId(this.idFicha).subscribe({
        next: (res: FichaPaciente) => {
          this.ficha = res;
          this.loading = false
        },
        error: (err: any) => {
          this.loading = false;
          this.exibirModal("Erro ao tentar encontrar ficha");
        }
      })
    }
  }

  ngOnInit(): void {
  }

  public scroll(id: string) {
    document.getElementById(id)?.scrollIntoView();
  }

  public buscarCep() {
    this.loading = true;
    let cep = this.ficha.cep.replaceAll("-", "").replaceAll(".", "");
    this.cepService.buscarCep(this.ficha.cep).subscribe({
      next: (res: DadosCEP) => {
        this.loading = false;
        this.ficha.endereco = res.logradouro;
        this.ficha.cidade = res.localidade;
        this.ficha.estado = res.uf;
      },
      error: (err: any) => {
        this.loading = false;
        this.exibirModal("CEP Inválido");
      }
    });
  }

  public salvarFicha() {
    if (this.idFicha == "NOVA") {
      this.gravarNovaFicha();
    }
    else {
      this.atualizarFichaExistente();
    }
  }

  public atualizarFichaExistente() {
    this.loading = true;
    this.fichaService.atualizarFicha(this.ficha).subscribe({
      next: (res: FichaPaciente) => {
        this.exibirModal("Ficha atualizada com Sucesso")
        this.loading = false;
        this.ficha = res;
        this.idFicha = res.idFicha != null ? res.idFicha.toString() : "";
      },
      error: () => {
        this.loading = false;
        this.exibirModal("Erro ao atualizar ficha");
      }
    });
  }

  public gravarNovaFicha() {
    this.loading = true;
    this.fichaService.cadastrarNovaFicha(this.ficha).subscribe({
      next: (res: FichaPaciente) => {
        this.exibirModal("Ficha cadastrada com Sucesso")
        this.loading = false;
        this.ficha = res;
        this.idFicha = res.idFicha != null ? res.idFicha.toString() : "";
      },
      error: () => {
        this.loading = false;
        this.exibirModal("Erro ao gravar nova ficha");
      }
    });
  }

  public voltarParaPaginaAnterior(): void {
    this.router.navigate(['main']);
  }

  public realizarUpload(data: any): void {
    let file = data.target.files[0];
    let formData = new FormData();
    formData.append("arquivo", file, file.name);
    this.loading = true
    this.uploadService.uploadFile(formData).subscribe({
      next: (res: PathToFile) => {
        this.loading = false;
        this.pathToFile = res;
        this.exibirModal("Upload Realizado");
      },
      error: (err: any) => {
        this.loading = false;
        this.exibirModal("falha ao exibir Upload");
      }
    })
  }

  public exibirModal(mensagem: String): void {
    this.mensagemModal = mensagem;
    document.getElementById("btnModalAlerta")?.click();
  }

  public chamarUpload(): void {
    document.getElementById("btnModalUpload")?.click();
  }
}
