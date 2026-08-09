import os
from pathlib import Path
from shutil import which

from selenium import webdriver
from selenium.common.exceptions import WebDriverException
from selenium.webdriver.chrome.options import Options as BraveOptions
from selenium.webdriver.chrome.service import Service as BraveService
from selenium.webdriver.firefox.options import Options as FirefoxOptions
from selenium.webdriver.firefox.service import Service as FirefoxService


def _abrir_brave():
    executavel = which("brave-browser") or which("brave")
    driver = which("chromedriver")
    if not executavel:
        raise RuntimeError("Brave nao foi encontrado no sistema.")
    if not driver:
        raise RuntimeError("ChromeDriver nao foi encontrado no sistema.")

    opcoes = BraveOptions()
    opcoes.binary_location = executavel
    perfil = Path.home() / ".aurora_selenium" / "brave"
    perfil.mkdir(parents=True, exist_ok=True)

    opcoes.add_argument(f"--user-data-dir={perfil}")
    opcoes.add_argument("--no-first-run")
    opcoes.add_argument("--no-default-browser-check")
    opcoes.add_argument("--disable-extensions")
    opcoes.add_argument("--disable-crash-reporter")
    opcoes.add_argument("--disable-features=Crashpad")
    opcoes.add_argument("--remote-debugging-pipe")

    return webdriver.Chrome(service=BraveService(driver), options=opcoes)


def _abrir_firefox():
    executavel = which("firefox") or which("firefox-esr")
    driver = which("geckodriver")
    if not executavel:
        raise RuntimeError("Firefox nao foi encontrado no sistema.")
    if not driver:
        raise RuntimeError("GeckoDriver nao foi encontrado no sistema.")

    opcoes = FirefoxOptions()
    opcoes.binary_location = executavel
    perfil = Path.home() / ".aurora_selenium" / "firefox"
    perfil.mkdir(parents=True, exist_ok=True)
    opcoes.add_argument("-profile")
    opcoes.add_argument(str(perfil))

    return webdriver.Firefox(service=FirefoxService(driver), options=opcoes)


def criar_navegador():
    preferencia = os.getenv("AURORA_BROWSER", "firefox").strip().lower()

    if preferencia not in {"brave", "firefox"}:
        raise ValueError("AURORA_BROWSER deve ser 'brave' ou 'firefox'.")

    candidatos = (
        [("Brave", _abrir_brave), ("Firefox", _abrir_firefox)]
        if preferencia == "brave"
        else [("Firefox", _abrir_firefox), ("Brave", _abrir_brave)]
    )

    erros = []
    for nome, abrir in candidatos:
        try:
            navegador = abrir()
            print(f"Navegador usado: {nome}")
            return navegador
        except (RuntimeError, WebDriverException) as erro:
            erros.append(f"{nome}: {erro}")

    detalhes = "\n".join(erros)
    raise RuntimeError(
        "Nao foi possivel abrir Brave nem Firefox. "
        "Feche outras instancias abertas com o perfil do robo e tente novamente.\n"
        f"{detalhes}"
    )
