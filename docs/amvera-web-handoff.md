# Handoff: PapaKarlo Web → прод-Amvera (где бэкенд)

Передайте этот файл коллеге с доступом к Amvera, где крутится бэкенд.  
Он может открыть его в Cursor и выполнить шаги в разделе «Коллега».

**Репозиторий:** https://github.com/xidealo/PapaKarlo  
**Ветка с деплой-пакетом:** `feature/web-kmp` (папка `amvera/`)  
**Бэкенд (не менять):** `https://fooddelivery-xidealo.amvera.io`  
**Тестовый web (потом Stop):** `ivanzheg/papakarlo-web`

---

## Кто что делает (кратко)

### Вы (владелец web / PapaKarlo)

1. Запушить ветку `feature/web-kmp` на GitHub (если ещё не запушили) — чтобы коллега видел `amvera/` и этот файл.
2. Отправить коллеге этот файл (`docs/amvera-web-handoff.md`) или ссылку на него в репо.
3. Когда коллега создаст приложение и пришлёт **Git URL** — на своей машине переключить remote и сделать первый деплой (или попросить коллегу, если у него есть код).
4. После того как сайт на проде ок — в тестовом аккаунте `ivanzheg` нажать **Stop** у `papakarlo-web`.
5. Свой домен у регистратора DNS (CNAME) — если домен на вас; либо коллега делает DNS, если зона у него.

### Коллега (доступ к прод-Amvera)

1. Создать **отдельное** Docker-приложение под web (не внутрь бэкенда).
2. Прислать вам Git URL нового приложения.
3. (Опционально) помочь с первым `git push`, если у него есть клон репо и доступ к Amvera git.
4. В панели Amvera привязать купленный домен к **web**-приложению.
5. Для новых кафе — добавлять поддомены на **то же** web-приложение.

---

## Вердикт по архитектуре

| Вопрос | Ответ |
|--------|--------|
| Тот же аккаунт Amvera, где бэкенд? | **Да** |
| То же приложение, что бэкенд? | **Нет** |
| Нужно второе приложение только под web? | **Да** (Docker + nginx) |
| Отдельное приложение на каждое кафе? | **Нет** — одно web + много доменов |

```text
Браузер → ваш-домен (web-приложение Amvera, nginx)
                ├─ статика (меню, wasm)
                └─ proxy API/images → fooddelivery-xidealo.amvera.io

Мобилки → напрямую fooddelivery-xidealo.amvera.io
```

---

## Коллега: создать приложение в Amvera

1. Войти в **прод**-аккаунт Amvera (где `fooddelivery-xidealo`).
2. Создать **новое** приложение, имя например: `papakarlo-web` / `fooddelivery-web`.
3. Тип: **Docker** (не Gradle / JVM).
4. Порт контейнера: **80**.
5. Скопировать **Git URL**:
   `https://git.msk0.amvera.ru/<ЛОГИН>/<ИМЯ_ПРОЕКТА>`
6. Написать вам этот URL (в чат / issue).

Корень PapaKarlo содержит `amvera.yml` под JVM — **для web не использовать**.  
Web деплоится из папки `amvera/` (`Dockerfile` + `nginx.conf` + `site/`).

---

## Вы или коллега: первый деплой

На машине с клоном PapaKarlo и доступом к git Amvera:

```bash
cd /path/to/PapaKarlo
git checkout feature/web-kmp
git pull

cd amvera
git remote -v
# сейчас обычно: https://git.msk0.amvera.ru/ivanzheg/papakarlo-web

git remote rename amvera amvera-test
git remote add amvera https://git.msk0.amvera.ru/<НОВЫЙ_ЛОГИН>/<ИМЯ_ПРОЕКТА>

# свежий бандл (если нужно пересобрать):
cd ..
./amvera/deploy.sh

cd amvera
git add -A
git commit -m "deploy web to production Amvera account"
git push amvera master
```

Merge `feature/web-kmp` → `master` PapaKarlo для Amvera **не обязателен**.

`nginx.conf` проксирует на `fooddelivery-xidealo.amvera.io` — **не менять**, пока бэкенд там.

### Проверка на временном `*.amvera.io`

- [ ] Открывается меню
- [ ] Картинки грузятся
- [ ] Логин / заказ работают
- [ ] Тест бренда: `https://<amvera-host>/?flavor=gustopub`

---

## Коллега: свой купленный домен

Домен вешать на **web-приложение**, не на бэкенд.

1. Amvera → web-приложение → **Домены** → добавить домен.
2. Взять DNS-инструкцию из панели (обычно CNAME на `*.amvera.io`).
3. У регистратора прописать запись.
4. Дождаться «активен» + SSL.

### Несколько кафе (потом)

Один web-билд сам выбирает бренд по имени хоста (`papakarlo`, `gustopub`, …).

```text
papakarlo.example.ru  →  CNAME → то же web-приложение Amvera
gustopub.example.ru   →  CNAME → туда же
taverna.example.ru    →  CNAME → туда же
```

В Amvera добавлять каждый домен в **то же** web-приложение.  
Новые Docker-приложения на каждое кафе **не создавать**.

---

## Вы: остановить тест

Когда прод проверен 1–2 дня:

1. Аккаунт **`ivanzheg`** → проект **`papakarlo-web`** → **Stop**.
2. Потом можно удалить тест.

Откат при проблемах:

```bash
cd amvera
git push amvera-test master
# Start тестового проекта в панели ivanzheg
```

---

## Чего не делать

- Не класть web в git/контейнер бэкенда.
- Не менять host бэкенда в `nginx.conf` без необходимости.
- Не направлять домен кафе на бэкенд-приложение.
- Не оставлять `netlify.toml` в `amvera/site/` (для Amvera не нужен; `deploy.sh` его удаляет).

---

## Файлы в репо (для Cursor)

- `amvera/Dockerfile`
- `amvera/nginx.conf`
- `amvera/deploy.sh`
- `amvera/site/` — собранный бандл
- `shared/src/jsMain/kotlin/com/bunbeauty/shared/WebFlavor.kt`
- `webApp/src/jsMain/kotlin/com/bunbeauty/web/Main.kt`
- `designsystem/.../FoodDeliveryCompany.kt` — список flavor’ов

### Промпт для Cursor коллеги

> Открой `docs/amvera-web-handoff.md` и выполни шаги «Коллега»:  
> отдельное Docker-приложение web на прод-Amvera (не внутрь backend),  
> порт 80, пришли Git URL. Потом помоги с remote/push и привязкой своего домена.  
> Бэкенд host `fooddelivery-xidealo.amvera.io` не менять.  
> Мульти-кафе — поддомены на то же web-приложение.

---

## Чеклист

- [ ] `feature/web-kmp` на GitHub (вы)
- [ ] Handoff передан коллеге (вы)
- [ ] Docker web-приложение создано, Git URL есть (коллега)
- [ ] Remote + `git push amvera master` (вы или коллега)
- [ ] Сайт ок на `*.amvera.io` (оба)
- [ ] Свой домен + DNS + SSL (коллега / вы)
- [ ] Тест `ivanzheg/papakarlo-web` Stop (вы)
