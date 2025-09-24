import styles from "./InputBox.module.css";

interface InputFields {
  name: string;
  id?: string;
  value?: string | number | readonly string[] | undefined;
  type?: string;
  size?: "sm" | "md" | "lg";
  placeholder?: string;
  disabled?: boolean; //This is for disabled styling
  onChange?: React.ChangeEventHandler<HTMLInputElement>;
}

function InputBox({
  name,
  id,
  value,
  type = "text",
  size = "sm",
  placeholder = "",
  disabled = false,
  onChange,
}: InputFields) {
  const inputStyles = [
    styles.input,
    styles[size],
    disabled ? styles.disabled : " ",
  ]
    .filter(Boolean)
    .join(" ");

  return (
    <>
      <input
        id={id}
        name={name}
        value={value}
        className={inputStyles}
        type={type}
        placeholder={placeholder}
        disabled={disabled}
        onChange={onChange}
      />
    </>
  );
}

//Example usage
/*    <InputBox
      label="Email"
      placeholder="Key in your email"
      disabled={false}
    ></InputBox>
*/

export default InputBox;
