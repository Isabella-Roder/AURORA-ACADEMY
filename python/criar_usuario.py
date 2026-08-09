from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import Select
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from pathlib import Path

from navegador import criar_navegador

navegador = criar_navegador()

espera = WebDriverWait(navegador, 20)

try :
    cadastro_url = (Path(__file__).resolve().parents[1] / "frontend/html/cadastro.html").as_uri()

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

    perfis = {
        "1": "ALUNO",
        "2": "PROFESSOR",
        "3": "ADMIN"
    }

    while True:
        escolha = input(
            "Escolha o perfil (1 = ALUNO, 2 = PROFESSOR, 3 = ADMIN): "
        ).strip()

        opt = perfis.get(escolha)
        if opt:
            break

        print("Opcao invalida. Digite somente 1, 2 ou 3.")
    
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
