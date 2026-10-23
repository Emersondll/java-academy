ALTER TABLE usuario RENAME TO app_user;
ALTER TABLE app_user RENAME COLUMN nome TO name;
ALTER TABLE app_user RENAME COLUMN usuario TO username;
ALTER TABLE app_user RENAME COLUMN senha TO password_hash;
ALTER TABLE app_user RENAME COLUMN modo TO navigation_mode;
ALTER TABLE app_user RENAME COLUMN criado_em TO created_at;

UPDATE app_user SET navigation_mode = 'FREE' WHERE navigation_mode = 'LIVRE';
UPDATE app_user SET navigation_mode = 'TRAIL' WHERE navigation_mode = 'TRILHA';

ALTER TABLE modulo RENAME TO learning_module;
ALTER TABLE learning_module RENAME COLUMN ordem TO position;
ALTER TABLE learning_module RENAME COLUMN etapa TO stage;
ALTER TABLE learning_module RENAME COLUMN titulo TO title;
ALTER TABLE learning_module RENAME COLUMN resumo TO summary;

ALTER TABLE aula RENAME TO lesson;
ALTER TABLE lesson RENAME COLUMN modulo_id TO module_id;
ALTER TABLE lesson RENAME COLUMN ordem TO position;
ALTER TABLE lesson RENAME COLUMN ordem_global TO global_order;
ALTER TABLE lesson RENAME COLUMN titulo TO title;
ALTER TABLE lesson RENAME COLUMN resumo TO summary;

ALTER TABLE progresso RENAME TO lesson_progress;
ALTER TABLE lesson_progress RENAME COLUMN usuario_id TO user_id;
ALTER TABLE lesson_progress RENAME COLUMN aula_id TO lesson_id;
ALTER TABLE lesson_progress RENAME COLUMN concluida_em TO completed_at;
