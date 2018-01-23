package biz.dealnote.messenger.api;

import biz.dealnote.messenger.api.model.Captcha;
import io.reactivex.Observable;

/**
 * Created by Ruslan Kolbasa on 06.06.2017.
 * phoenix
 */
public interface ICaptchaProvider {

    /**
     * Запросить ввод капчи
     * После выполнения этого метода следует периодически проверять {@link biz.dealnote.messenger.api.ICaptchaProvider#lookupCode}
     * @param sid код капчи
     * @param captcha капча
     */
    void requestCaptha(String sid, Captcha captcha);

    /**
     * Отменить запрос капчи
     * @param sid код капчи
     */
    void cancel(String sid);

    /**
     * Слушать отмену запроса капчи
     * @return "паблишер" кода капчи
     */
    Observable<String> observeCanceling();

    /**
     * Проверить, не появился ли введенный текст капчи
     * @param sid код капчи
     * @return введенный пользователем текст с картинки
     * @throws OutOfDateException если капча больше не обрабатывается
     */
    String lookupCode(String sid) throws OutOfDateException;

    /**
     * Этот "паблишер" уведомляет о том, что ожидается ввод капчи
     * Если наблюдатель получил уведомление отсюда - должен оповестить
     * с помощью метода {@link biz.dealnote.messenger.api.ICaptchaProvider#notifyThatCaptchaEntryActive}
     * о том, что активен и ожадает ввода пользователя
     * @return "паблишер" кода капчи
     */
    Observable<String> observeWaiting();

    /**
     * Уведомдить провайдер о том, что пользователь все еще в процессе ввода текста
     * @param sid код капчи
     */
    void notifyThatCaptchaEntryActive(String sid);

    /**
     * Сохранение введенного пользователем текста с картинки
     * @param sid код капчи
     * @param code текст с картинки
     */
    void enterCode(String sid, String code);
}
