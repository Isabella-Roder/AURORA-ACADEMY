from pathlib import Path

from selenium import webdriver
from selenium.common.exceptions import WebDriverException
from selenium.webdriver.common.by import By
from selenium.webdriver.edge.options import Options
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.ui import WebDriverWait


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
    print("Erro original:")
    print(erro)
    raise

espera = WebDriverWait(navegador, 20)

try:
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

    link_conteudo = espera.until(
        EC.element_to_be_clickable((By.CSS_SELECTOR, "a[href^='professor-conteudo.html?id=']"))
    )

    navegador.execute_script("arguments[0].click();", link_conteudo)

    espera.until(
        EC.presence_of_element_located((By.ID, "form-modulo"))
    )

    navegador.find_element(By.ID, "modulo-titulo").send_keys("Modulo criado pelo robo")
    navegador.find_element(By.ID, "modulo-ordem").send_keys("1")

    navegador.execute_script(
        "document.getElementById('form-modulo').requestSubmit();"
    )

    espera.until(
        lambda driver: driver.find_element(By.ID, "mensagem-modulo").text.strip() != ""
    )

    print("Mensagem do modulo:", navegador.find_element(By.ID, "mensagem-modulo").text)

    form_aula = espera.until(
        EC.presence_of_element_located((By.CSS_SELECTOR, ".aula-form"))
    )

    form_aula.find_element(By.CSS_SELECTOR, ".aula-titulo").send_keys("Aula criada pelo robo")
    form_aula.find_element(By.CSS_SELECTOR, ".aula-descricao").send_keys("Aula criada automaticamente pelo Selenium.")
    form_aula.find_element(By.CSS_SELECTOR, ".aula-url").send_keys("https://www.youtube.com/watch?v=dQw4w9WgXcQ")
    form_aula.find_element(By.CSS_SELECTOR, ".aula-duracao").send_keys("00:15")
    form_aula.find_element(By.CSS_SELECTOR, ".aula-ordem").send_keys("1")

    navegador.execute_script(
        "arguments[0].requestSubmit();",
        form_aula
    )

    espera.until(
        lambda driver: form_aula.find_element(By.CSS_SELECTOR, ".aula-mensagem").text.strip() != ""
    )

    print("Mensagem da aula:", form_aula.find_element(By.CSS_SELECTOR, ".aula-mensagem").text)

finally:
    input("Pressione Enter para fechar o navegador...")
    navegador.quit()