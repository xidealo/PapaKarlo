# Handoff: GoatFood лендинг → Amvera + goatfood.ru

Маркетинговый сайт из `landing/`. Деплой-пакет: `landing-amvera/` (как `amvera/` для webApp заказов).

**Репозиторий:** https://github.com/xidealo/PapaKarlo  
**Домен:** `https://goatfood.ru`  
**Пакет:** `landing-amvera/` (Docker + nginx + статика)

---

## Что сделать в Amvera (вручную)

1. Войти в аккаунт Amvera, где будут продакшен-сайты.
2. Создать **новое** приложение (не backend `fooddelivery-xidealo`, не papakarlo-web):
   - имя, например: `goatfood-landing`;
   - тип: **Docker**;
   - порт контейнера: **80**.
3. Скопировать **Git URL** (вида `https://git.msk0.amvera.ru/<логин>/goatfood-landing`).
4. После первого `git push` (ниже) дождаться деплоя на временный `*.amvera.io`.
5. Приложение → **Домены** → добавить `goatfood.ru`.
6. У регистратора DNS — CNAME (или A) по инструкции из панели Amvera.
7. Дождаться «активен» + SSL.
8. Проверить: `https://goatfood.ru` открывает лендинг GoatFood.

Не класть лендинг внутрь контейнера бэкенда и не вешать `goatfood.ru` на приложение заказов.

Если раньше на `goatfood.ru` жил API — DNS нужно переключить на это Docker-приложение; API оставить на `fooddelivery-xidealo.amvera.io` (или отдельном хосте).

---

## Первый деплой (на машине с клоном PapaKarlo)

```bash
cd /path/to/PapaKarlo
./landing-amvera/deploy.sh

cd landing-amvera
git init   # если ещё нет nested git
git remote add amvera https://git.msk0.amvera.ru/<ЛОГИН>/goatfood-landing
git add -A
git commit -m "deploy GoatFood landing"
git push -u amvera master
```

Повторные обновления:

```bash
./landing-amvera/deploy.sh
cd landing-amvera
git add -A
git commit -m "update"
git push amvera master
```

Корень PapaKarlo содержит `amvera.yml` под ordering web — **для лендинга не использовать**.  
Лендинг пушится **из** папки `landing-amvera/` (Dockerfile рядом с `site/`).

---

## Архитектура

```text
Браузер → goatfood.ru (Docker-приложение Amvera, nginx)
                └─ статика из landing-amvera/site/
```

API заказов / papakarlo-web — отдельные приложения, этот пакет их не проксирует.

---

## Файлы

- `landing/` — исходники Vite/React
- `landing-amvera/Dockerfile`
- `landing-amvera/nginx.conf`
- `landing-amvera/deploy.sh`
- `landing-amvera/site/` — собранный бандл

---

## Чеклист

- [ ] Docker-приложение `goatfood-landing` создано, Git URL есть
- [ ] `./landing-amvera/deploy.sh` + `git push amvera master`
- [ ] Сайт ок на `*.amvera.io`
- [ ] Домен `goatfood.ru` + DNS + SSL
- [ ] На сайте бренд GoatFood, не BunBeauty
