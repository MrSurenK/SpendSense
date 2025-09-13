interface ButtonProps {
  name: string;
}

function Button({ name }: ButtonProps) {
  return (
    <>
      <button>{name}</button>
    </>
  );
}

export default Button;
