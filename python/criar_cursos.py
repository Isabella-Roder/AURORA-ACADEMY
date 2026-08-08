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

espera = WebDriverWait(navegador, 10)

try :
    login_url = Path("C:/Users/isabe/Aurora Academy/frontend/html/login.html").as_uri()

    navegador.get(login_url)

    espera.until(
        EC.presence_of_element_located((By.ID, "email-login"))
    ).send_keys("isabellaprof@auroraacademy.com")

    espera.until(
        EC.presence_of_element_located((By.ID, "senha-login"))
    ).send_keys("1234")

    espera.until(
        EC.element_to_be_clickable((By.ID, "btn-login"))
    ).click()

    espera.until(
        EC.url_contains("professor-dashboard.html")
    )

    link_criar_curso = espera.until(
        EC.presence_of_element_located((By.CSS_SELECTOR, "a[href='professor-curso.html']"))
    )

    navegador.execute_script("arguments[0].click();", link_criar_curso)

    espera.until(
        EC.presence_of_element_located((By.ID, "curso-titulo"))
    ).send_keys("Curso criado pelo robo")

    navegador.find_element(By.ID, "curso-descricao").send_keys("Esse curso foi criado usando Selenium.")
    navegador.find_element(By.ID, "curso-categoria").send_keys("Programacao")

    Select(navegador.find_element(By.ID, "curso-nivel")).select_by_value("INICIANTE")

    navegador.find_element(By.ID, "curso-carga-horaria").send_keys("10:00")
    navegador.find_element(By.ID, "curso-preco").send_keys("149.90")
    navegador.find_element(By.ID, "curso-img-url").send_keys("https://images.unsplash.com/photo-1516321318423-f06f85e504b3")

    Select(navegador.find_element(By.ID, "curso-status")).select_by_value("PUBLICADO")

    espera.until(
        EC.element_to_be_clickable((By.ID, "btn-salvar-curso"))
    )

    navegador.execute_script(
        "document.getElementById('form-curso').requestSubmit();"
    )

    espera.until(
        lambda driver: driver.find_element(By.ID, "mensagem-curso").text.strip() != ""
    )

    mensagem = navegador.find_element(By.ID, "mensagem-curso")
    print("Mensagem da tela", mensagem.text)

finally :
    input("Pressione Enter para fechar o navegador...")
    navegador.quit()
