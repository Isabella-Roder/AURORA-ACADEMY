from selenium import webdriver
from selenium.webdriver.edge.options import Options
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import Select
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from pathlib import Path
from selenium.common.exceptions import WebDriverException

opcoes = Options()

perfil_robo = Path.home() / "aurora_selenium_profile"
perfil_robo.mkdir(exist_ok=True)

opcoes.add_argument(f"--user-data-dir={perfil_robo}")
opcoes.add_argument("--no-first-run")
opcoes.add_argument("--no-default-browser-check")
opcoes.add_argument("--disable-extensions")
opcoes.add_argument("--disable-crash-reporter")
opcoes.add_argument("--disable-features=Crashpad")
opcoes.add_argument("--remote-debugging-pipe")

try:
    navegador = webdriver.Edge(options=opcoes)
except WebDriverException as erro:
    print("Nao consegui abrir o Microsoft Edge pelo Selenium.")
    print("Feche todas as janelas do Edge e tente rodar de novo.")
    print("Se continuar, provavelmente o EdgeDriver nao esta compativel com sua versao do Edge.")
    print("Erro original:")
    print(erro)
    raise

espera = WebDriverWait(navegador, 20)

try :
    cadastro_url = Path("C:/Users/isabe/Aurora Academy/frontend/html/cadastro.html").as_uri()

    navegador.get(cadastro_url)

    espera.until(
        EC.presence_of_element_located((By.ID, "nome"))
    ).send_keys("Selenium Teste")

    espera.until(
        EC.presence_of_element_located((By.ID, "email"))
    ).send_keys("seleniumcontateste@aurora.com")

    espera.until(
        EC.presence_of_element_located((By.ID, "senha"))
    ).send_keys("123456")

    espera.until(
        EC.presence_of_element_located((By.ID, "confirmar-senha"))
    ).send_keys("123456")

    opt = int(input("1. para ALUNO, 2. para PROFESSOR, .3 para ADMIN"))

    if opt == 1 :
        opt = "ALUNO"
    elif opt == 2 :
        opt = "PROFESSOR"
    else :
        opt = "ADMIN"
    
    Select(navegador.find_element(By.ID, "perfil")).select_by_value(opt)

    espera.until(
        EC.element_to_be_clickable((By.ID, "btn-cadastrar"))
    )

    navegador.execute_script(
        "document.getElementById('form-cadastro').requestSubmit();"
    )

    espera.until(
        lambda driver: driver.find_element(By.ID, "mensagem-cadastro").text.strip() != ""
    )

    mensagem = navegador.find_element(By.ID, "mensagem-cadastro")
    print("Mensagem da tela", mensagem.text)

finally :
    input("Pressione Enter para fechar o navegador...")
    navegador.quit()
