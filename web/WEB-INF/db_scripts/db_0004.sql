ALTER TABLE public.buy_sells ADD feedback INT NULL;
ALTER TABLE public.buy_sells
  ADD CONSTRAINT buy_sells_feedbacks_id_fk
FOREIGN KEY (feedback) REFERENCES feedbacks (id) ON DELETE SET NULL;

ALTER TABLE public.products ALTER COLUMN type SET NOT NULL;
ALTER TABLE public.products ALTER COLUMN type TYPE VARCHAR(16) USING type::VARCHAR(16);