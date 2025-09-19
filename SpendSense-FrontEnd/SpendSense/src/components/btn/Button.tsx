import styles from "./Button.module.css";

interface ButtonProps {
  text: string;
  variant?: "btn-primary" | "btn-secondary";
  size?: "sm" | "md" | "lg";
  disabled?: boolean;
  onClick?: () => void;
}

function Button({
  text,
  variant = "btn-primary",
  size = "lg",
  disabled = false,
  onClick,
}: ButtonProps) {
  const buttonStyles = [
    styles.btn,
    styles[variant],
    styles[size],
    disabled ? styles.disabled : " ",
  ]
    .filter(Boolean)
    .join(" ");

  return (
    <>
      <button className={buttonStyles} onClick={onClick} disabled={disabled}>
        <span>{text}</span>
      </button>
    </>
  );
}

export default Button;
