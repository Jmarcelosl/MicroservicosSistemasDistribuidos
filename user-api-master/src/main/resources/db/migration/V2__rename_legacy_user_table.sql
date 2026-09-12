DO $$
BEGIN
    IF to_regclass('users.usuario') IS NOT NULL
       AND to_regclass('users."user"') IS NULL THEN
        ALTER TABLE users.usuario RENAME TO "user";
    END IF;
END $$;
