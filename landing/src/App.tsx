import { type FormEvent, type ReactNode, useEffect, useRef, useState } from 'react';
import { ArrowDownRight, ArrowRight, Check, ChevronDown, Menu, Plus, X } from 'lucide-react';
import { Route, Switch, useLocation, Router as WouterRouter } from 'wouter';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { ErrorBoundary } from '@/components/error-boundary';
import { Toaster } from '@/components/ui/toaster';
import { TooltipProvider } from '@/components/ui/tooltip';
import NotFound from '@/pages/not-found';

const queryClient = new QueryClient();

const faqs = [
  ['Что такое BunBeauty?', 'BunBeauty – платформа для приёма заказов. У кафе своё мобильное приложение и при необходимости сайт. Заказы приходят администратору в отдельное приложение.'],
  ['Как заказывает гость?', 'Через ваше приложение в App Store и Google Play или через сайт. Меню с фото, доставка или самовывоз – как настроите на смене.'],
  ['Как подключиться?', 'Оставьте заявку, и мы созвонимся на 20 минут. После этого соберём первую версию меню и запустим её в течение нескольких рабочих дней.'],
  ['Какая комиссия за заказ?', 'Платформа берёт 3% с заказа. Никаких скрытых платежей, платы за установку или длинных контрактов.'],
];

function Logo() {
  return <span className="bb-logo" aria-label="BunBeauty"><span className="bb-logo-mark">b.</span><span className="bb-logo-word">BunBeauty</span></span>;
}

function PhonePreview() {
  return (
    <div className="bb-stage" aria-label="Предпросмотр мобильного меню BunBeauty" data-testid="preview-phone">
      <div className="bb-orbit" />
      <div className="bb-phone">
        <div className="bb-phone-top"><span>9:41</span><span className="bb-phone-user" aria-hidden="true" /></div>
        <div className="bb-phone-banner"><strong>Сделано<br />с заботой</strong><small>Скидка 15% на первый заказ</small><span className="bb-cup" /></div>
        <div className="bb-phone-section"><h3>Меню</h3><span>Популярное</span></div>
        <div className="bb-menu-row"><span className="bb-food-art" /><span className="bb-menu-meta"><strong>Флэт уайт</strong><small>двойной эспрессо, молоко</small></span><span className="bb-menu-price">250 ₽</span></div>
        <div className="bb-menu-row"><span className="bb-food-art" /><span className="bb-menu-meta"><strong>Чизкейк баскский</strong><small>сливочный сыр, ваниль</small></span><span className="bb-menu-price">340 ₽</span></div>
        <div className="bb-menu-row"><span className="bb-food-art" /><span className="bb-menu-meta"><strong>Матча-тоник</strong><small>матча, тоник, лайм</small></span><span className="bb-menu-price">290 ₽</span></div>
        <div className="bb-phone-section"><h3>Комбо дня</h3><span>ещё 4 позиции</span></div>
        <div className="bb-menu-row"><span className="bb-food-art" /><span className="bb-menu-meta"><strong>Кофе + круассан</strong><small>идеальное начало</small></span><span className="bb-menu-price">420 ₽</span></div>
      </div>
      <div className="bb-floating-tag"><span>ваш бренд</span>в кармане</div>
    </div>
  );
}

function DashboardPreview() {
  return (
    <div className="bb-dashboard bb-card-dark" data-testid="preview-dashboard">
      <div className="bb-dashboard-top"><strong>Доброе утро, «Зёрна»</strong><small>ОБЗОР · 07 ИЮНЯ 2024</small></div>
      <div className="bb-stat-grid">
        <div className="bb-stat"><span>Заказов сегодня</span><strong>48 <em>+18%</em></strong></div>
        <div className="bb-stat"><span>Выручка</span><strong>31 240 ₽</strong></div>
        <div className="bb-stat"><span>Средний чек</span><strong>650 ₽</strong></div>
      </div>
      <div className="bb-chart"><span className="bb-chart-label">Заказы с телефона · последние 7 дней</span><svg viewBox="0 0 500 140" preserveAspectRatio="none" aria-hidden="true"><path className="bb-chart-fill" d="M0 118 C40 105, 50 96, 89 105 S130 116, 157 80 S205 97, 233 69 S276 80, 300 55 S342 76, 372 43 S416 70, 455 35 S480 45, 500 18 L500 140 L0 140 Z" /><path className="bb-chart-line" d="M0 118 C40 105, 50 96, 89 105 S130 116, 157 80 S205 97, 233 69 S276 80, 300 55 S342 76, 372 43 S416 70, 455 35 S480 45, 500 18" /></svg></div>
    </div>
  );
}

function ContactModal({ onClose }: { onClose: () => void }) {
  const [sent, setSent] = useState(false);
  const nameRef = useRef<HTMLInputElement>(null);
  const submit = (event: FormEvent<HTMLFormElement>) => { event.preventDefault(); setSent(true); };
  useEffect(() => {
    const onKeyDown = (event: KeyboardEvent) => { if (event.key === 'Escape') onClose(); };
    document.addEventListener('keydown', onKeyDown);
    return () => document.removeEventListener('keydown', onKeyDown);
  }, [onClose]);
  useEffect(() => { nameRef.current?.focus(); }, []);
  return (
    <div className="bb-modal-backdrop" role="presentation" onMouseDown={(event) => event.target === event.currentTarget && onClose()}>
      <div className="bb-modal bb-card-dark" role="dialog" aria-modal="true" aria-labelledby="contact-title" aria-describedby={sent ? undefined : 'contact-intro'}>
        <button className="bb-modal-close" onClick={onClose} aria-label="Закрыть форму" data-testid="button-close-contact"><X size={20} /></button>
        {sent ? <div className="bb-success" role="status"><strong>Заявка отправлена</strong><span>Спасибо! Мы напишем вам в течение рабочего дня, чтобы назначить короткий разговор.</span></div> : <>
          <span className="bb-section-label">поговорим о вашем кафе</span>
          <h2 id="contact-title" className="bb-display">Запустим<br /><span className="bb-lime">ваше меню</span></h2>
          <p id="contact-intro">Расскажите о кафе – покажем, как BunBeauty будет выглядеть именно для вас.</p>
          <form className="bb-form" onSubmit={submit}>
            <label htmlFor="contact-name">Имя<input ref={nameRef} id="contact-name" className="bb-input" name="name" placeholder="Как к вам обращаться?" required data-testid="input-contact-name" /></label>
            <label htmlFor="contact-contact">Контакт<input id="contact-contact" className="bb-input" name="contact" placeholder="Телефон или Telegram" required data-testid="input-contact-contact" /></label>
            <label htmlFor="contact-email">Почта<input id="contact-email" className="bb-input" name="email" type="email" placeholder="hello@mail.ru" required data-testid="input-contact-email" /></label>
            <label htmlFor="contact-message">О кафе<textarea id="contact-message" className="bb-input" name="message" rows={3} placeholder="Название и город" data-testid="input-contact-message" /></label>
            <button className="bb-button bb-button-primary" type="submit" data-testid="button-submit-contact">Отправить заявку <ArrowRight size={16} /></button>
          </form>
        </>}
      </div>
    </div>
  );
}

function Home() {
  const [menuOpen, setMenuOpen] = useState(false);
  const [modalOpen, setModalOpen] = useState(false);
  const [openFaq, setOpenFaq] = useState<number | null>(0);
  const openContact = () => { setModalOpen(true); setMenuOpen(false); };
  useEffect(() => {
    document.body.style.overflow = modalOpen ? 'hidden' : '';
    return () => { document.body.style.overflow = ''; };
  }, [modalOpen]);
  return (
    <main className="bb-page">
      <div className="bb-container">
        <header className="bb-header">
          <a className="bb-logo" href="#top" data-testid="link-home"><Logo /></a>
          <nav className={`bb-nav ${menuOpen ? 'is-open' : ''}`} aria-label="Основная навигация">
            <a href="#product" onClick={() => setMenuOpen(false)} data-testid="link-product">Продукт</a>
            <a href="#conditions" onClick={() => setMenuOpen(false)} data-testid="link-conditions">Условия</a>
            <a href="#faq" onClick={() => setMenuOpen(false)} data-testid="link-faq">Вопросы</a>
          </nav>
          <button className="bb-menu" onClick={() => setMenuOpen(!menuOpen)} aria-label="Открыть меню" aria-expanded={menuOpen} data-testid="button-mobile-menu"><Menu size={22} /></button>
          <button className="bb-header-cta" onClick={openContact} data-testid="button-header-contact">Обсудить запуск <ArrowDownRight size={15} /></button>
        </header>
      </div>

      <section className="bb-container bb-hero" id="top">
        <div className="bb-hero-grid">
          <div>
            <div className="bb-eyebrow bb-reveal">своё приложение и сайт для кафе</div>
            <h1 className="bb-display bb-reveal bb-delay-1">Кафе,<br />которое <em>заказывают</em><br />с телефона</h1>
            <p className="bb-lede bb-reveal bb-delay-2">BunBeauty – платформа заказов для кафе. Гость оформляет заказ в вашем приложении или на сайте, администратор ведёт его в отдельном приложении.</p>
            <div className="bb-hero-actions bb-reveal bb-delay-3"><button className="bb-button bb-button-primary" onClick={openContact} data-testid="button-hero-contact">Запустить BunBeauty <ArrowRight size={16} /></button><a className="bb-button bb-button-ghost" href="#product" data-testid="link-hero-product">Посмотреть как это работает <ArrowDownRight size={17} /></a></div>
            <p className="bb-small-note">приложение для гостя · приложение для смены</p>
            <p className="bb-store-links">
              <a href="https://apps.apple.com/ru/developer/mark-shavlovskiy/id1651086345" target="_blank" rel="noreferrer" data-testid="link-app-store">App Store</a>
              <a href="https://play.google.com/store/apps/developer?id=xIdealo&hl=ru" target="_blank" rel="noreferrer" data-testid="link-google-play">Google Play</a>
              <a href="https://play.google.com/store/apps/details?id=com.bunbeauty.fooddeliveryadmin&hl=ru" target="_blank" rel="noreferrer" data-testid="link-admin-app">Приложение админа</a>
            </p>
          </div>
          <PhonePreview />
        </div>
      </section>

      <section className="bb-product bb-section bb-on-lime" id="product">
        <div className="bb-container">
          <div className="bb-product-intro"><div><span className="bb-section-label">01 / продукт</span><h2 className="bb-display bb-section-heading">Не просто меню<br /><em>Новый ритм кафе</em></h2></div><p>Статусы, стоп-лист и время доставки – гость видит это в момент заказа</p></div>
          <div className="bb-feature-layout">
            <div className="bb-feature-list">
              <article className="bb-feature"><span className="bb-feature-number">01</span><h3>Статусы под контролем</h3><p>Администратор ведёт заказ от нового до готов. Гость не звонит «ну что там»</p></article>
              <article className="bb-feature"><span className="bb-feature-number">02</span><h3>Стоп-лист без сюрпризов</h3><p>Скрыли позицию – её нельзя заказать. Никто не купит то, чего уже нет</p></article>
              <article className="bb-feature"><span className="bb-feature-number">03</span><h3>Режим под смену</h3><p>Доставка, самовывоз или оба. Курьеры не вышли – оставляете только самовывоз. Время доставки крутите от загрузки, гость видит его в заказе</p></article>
            </div>
            <DashboardPreview />
          </div>
        </div>
      </section>

      <section className="bb-conditions bb-section bb-on-dark" id="conditions">
        <div className="bb-container">
          <span className="bb-section-label">02 / условия</span>
          <h2 className="bb-display bb-section-heading">Платите только<br />когда <em>зарабатываете</em></h2>
          <div className="bb-condition-grid">
            <div className="bb-commission-card bb-card-lime"><span className="bb-label">КОМИССИЯ С ЗАКАЗА</span><div className="bb-commission"><strong>3</strong><span>%</span></div></div>
            <div className="bb-condition-copy bb-card-dark"><h3>Честная модель<br />для живого бизнеса</h3><p>Никаких абонентских платежей, платы за запуск и обязательств на год. BunBeauty растёт вместе с вашим кафе.</p><div className="bb-checks"><span><i /><Check size={13} /> Бесплатный запуск</span><span><i /><Check size={13} /> Поддержка 7 дней</span></div></div>
            <div className="bb-band bb-card-paper"><strong>Первый месяц – за наш счёт</strong><span>Проверим всё на реальных заказах</span></div>
          </div>
        </div>
      </section>

      <section className="bb-faq-section bb-section bb-on-lime" id="faq">
        <div className="bb-container bb-faq-wrap">
          <div><span className="bb-section-label">03 / вопросы</span><h2 className="bb-display bb-section-heading">Всё важное –<br /><em>здесь</em></h2></div>
          <div><div className="bb-faq-list">{faqs.map(([question, answer], index) => <div className="bb-faq-item" key={question}><button className="bb-faq-question" onClick={() => setOpenFaq(openFaq === index ? null : index)} aria-expanded={openFaq === index} data-testid={`button-faq-${index}`}><span>{question}</span>{openFaq === index ? <Plus size={18} /> : <ChevronDown size={18} />}</button><div className={`bb-faq-answer ${openFaq === index ? 'open' : ''}`}><p>{answer}</p></div></div>)}</div><div className="bb-faq-side bb-card-dark"><span className="bb-section-label">не нашли ответ?</span><p>Расскажем всё про ваш формат, город и меню – без презентаций на 48 слайдов.</p><button className="bb-button bb-button-primary" onClick={openContact} data-testid="button-faq-contact">Задать вопрос <ArrowRight size={15} /></button></div></div>
        </div>
      </section>

      <div className="bb-cta-section bb-on-dark">
        <section className="bb-container bb-cta">
          <span className="bb-section-label">готовы попробовать?</span><h2 className="bb-display">Ваше кафе<br /><span>В его телефоне</span></h2>
          <div className="bb-cta-row"><button className="bb-button bb-button-primary" onClick={openContact} data-testid="button-cta-contact">Поговорить о запуске <ArrowRight size={16} /></button><p>20 минут, чтобы понять, подходит ли вам BunBeauty</p></div>
        </section>
        <footer className="bb-container bb-footer">
          <div className="bb-footer-row">
            <a href="#top" data-testid="link-footer-home"><Logo /></a>
            <a href="mailto:shavl.mark@yandex.ru" data-testid="link-email">shavl.mark@yandex.ru</a>
          </div>
          <p className="bb-footer-legal">© 2026 BunBeauty · приложения и сайт заказов для кафе</p>
          <p className="bb-footer-legal">ИП Шавловский Марк Вячеславович · ОГРНИП 322695200049377 · ИНН 691010434605</p>
        </footer>
      </div>
      {modalOpen && <ContactModal onClose={() => setModalOpen(false)} />}
    </main>
  );
}

function RoutedErrorBoundary({ children }: { children: ReactNode }) {
  const [location] = useLocation();
  return <ErrorBoundary resetKey={location}>{children}</ErrorBoundary>;
}

function Router() {
  return <RoutedErrorBoundary><Switch><Route path="/" component={Home} /><Route component={NotFound} /></Switch></RoutedErrorBoundary>;
}

function App() {
  return <QueryClientProvider client={queryClient}><TooltipProvider><WouterRouter base={import.meta.env.BASE_URL.replace(/\/$/, '')}><Router /></WouterRouter><Toaster /></TooltipProvider></QueryClientProvider>;
}

export default App;